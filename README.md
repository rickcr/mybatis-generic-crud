# mybatis-generic-crud
MyBatis module illustrating using a GenericCrudMapper and GenericCrudService

Many times your application consists of doing common CRUD type operations (Create, Read, Update, Delete.)

I'm a big fan of MyBatis and a few solutions have been proposed to handle some of the basic CRUD in a generic fashion. This is just one 'partially generic' way to handle these common things. I say 'partially' since I've seen other approaches, such as having a single mapper and you pass in the table name and a list of columns etc. That approach is decent as well, but I've found that many times you start out generic but you'll often have to tweak a few things such that your CRUD becomes a bit more complicated. Following the approach below you have the flexibility to override whatever you want.

The drawback (minor) is that, yes, you still need to create a place-holder mapper interface and you still need to create your mapper xml file and the corresponding CRUD sql in it.

The benefit is that if you follow the convention of naming the mapper methods to match the GenericCrudMapper, you typically are only writing the SQL, and the Java side becomes simple.

Note, I also created a GenericCrudService class. If you follow any of my other tutorials you'll realize that I do prefer to have my Mappers used within a service class. This is just my preference and not required.  I've found in the 'real world' that I often need to do more stuff around my basic CRUD operations so that using the Mapper within a Service class gives me an extra layer to shield the client - typically the UI - from having to handle the other needed business logic around the CRUD operations.

To test this application, you should just be able to run "mvn clean install" or you can typically right click on the tests in your IDE and run them there.

The GenericCrudMapper looks like:

	public interface GenericCrudMapper<T, PK> {
		Object fetchById(PK id);
		List<T> fetch(@Param("searchCriteria") SearchCriteria searchCriteria);
		void insert(T o);
		void update(T o);
		void delete(T o);
	}

SearchCriteria is just a marker interface. I found in many of my apps the client wants to filter back by various conditions. In this case you'd make an implementation
 of SearchCriteria and add what you want to filter back by. Note, that in the GenericCrudService class there is a fetchAll. This just passes "null" to the fetch method,
 and in the mapper xml if there is no searchCriteria (null) it doesn't filter back by anything.
 
 To make an insatnce of a GenericCrudMapper just extend it passing in the proper type:
 
	 public interface EmployeeMapper extends GenericCrudMapper<Employee, Long> {
	 }
 
 The GenericCrudService looks like:
 
	 public abstract class GenericCrudService<T, PK> {
	 
		protected GenericCrudMapper mapper;
	 
		public GenericCrudService(GenericCrudMapper mapper) {
			this.mapper = mapper;
		}
	 
		public T fetch(PK id) {
			return (T)mapper.fetchById(id);
		}
	 
		public List<T> fetchAll() {
			return mapper.fetch(null);
		}
	 
		public List<T> fetch(SearchCriteria searchCriteria) {
			return mapper.fetch(searchCriteria);
		}
	 
		public void insert(T o) {
			mapper.insert(o);
		}
	 
		public void update(T o) {
			mapper.update(o);
		}
	 
		public void delete(T o) {
			mapper.delete(o);
		}
	 
	 }
 
 An implementation of this class requires you to pass it the proper Mapper to use (NOTE: someone let me know if there is a better way to do this?)
 
 
	 @Service
	 public class EmployeeService extends GenericCrudService<Employee, Long> {
 
		 @Autowired
		 public EmployeeService(EmployeeMapper mapper) {
			 super(mapper);
		 }
	 }


That's about it. You can start out using the GenericCrudMapper/Service and then if you have a case where one of your methods is different just override it, or 
add as many different ones as you like to your concrete implementation.




