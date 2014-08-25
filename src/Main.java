import java.io.FileNotFoundException;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.*;
import com.sk.cache.wrappers.loaders.*;

public class Main {
	public static void main(String[] args) {
		DataSource ds;
		try {
			ds = new DataSource(DataSource.getDefaultCacheDirectory());
			CacheSystem cache = new CacheSystem(ds);
			NpcDefinitionLoader loader = new NpcDefinitionLoader(cache);
			
			NpcDefinition id = loader.load(14262);
			
			Model model = new ModelLoader(cache).load(id.modelIds[0]);			
			
			System.out.println(id.name);
			System.out.println(model.getId());
			System.out.println(model.vertexCount);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
