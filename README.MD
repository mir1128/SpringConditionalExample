# 用Conditional注解决定是否声明Bean

只在某些条件满足的时候才声明一个Bean

某些条件包括：

1. 通过property文件配置
2. 通过环境变量
3. 通过其他Bean是否声明
4. 其他类定义


在configuration类声明一个Bean的时候

	@Configuration
	public class SpringConditionalConfiguration {

    	@Bean
    	@Conditional(MagicExistsCondition.class)
    	public MagicBean magicBean() {
        	return new MagicBean();
    	}
	}


`MagicExistsCondition`类需要实现Condition接口，Condition接口唯一操作就是`matches`, `matches`接口被调用的时候会传入两个参数
`ConditionContext`和`AnnotatedTypeMetadata`，通过`ConditionContext`获取环境配置信息，通过`AnnotatedTypeMetadata`获取类注解信息


	public class MagicExistsCondition implements Condition{
	    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
	        ResourceLoader resourceLoader = context.getResourceLoader();
	        Resource resource = resourceLoader.getResource("application.properties");
	        Properties properties = new Properties();
	        try {
	            properties.load(resource.getInputStream());
	
	            return properties.containsKey("magic");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return true;
	    }
	}
	
测试类

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(classes = SpringConditionalConfiguration.class)
	public class MagicBeanTest {
	    @Autowired
	    MagicBean magicBean;
	
	    @Test
	    public void should_declare_magic_bean() throws Exception {
	        assertNotNull(magicBean);
	    }
	}			


