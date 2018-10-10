package uk.gov.dvla.osl.commons.metadata;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MetadataUnitTest {

    @Test
    public void InstantiationTest() throws IOException, ClassNotFoundException{
        ThreadLocal<HashMap<String, String>> meta = ThreadLocalMetadata.get();
        Assert.assertNotNull(meta);
    }

    @Test
    public void UpdateSetTest() throws IOException, ClassNotFoundException{
        ThreadLocalMetadata.clear();
        ThreadLocalMetadata.set("x", "y");
        Assert.assertEquals("y", ThreadLocalMetadata.get("x"));
    }

    @Test
    public void AssignSetTest() throws IOException, ClassNotFoundException{
        ThreadLocalMetadata.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "b");
        ThreadLocalMetadata.set(map);
        Assert.assertEquals("b", ThreadLocalMetadata.get("a"));
    }

    @Test
    public void AppendTest() throws IOException, ClassNotFoundException{
        ThreadLocalMetadata.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("i", "j");
        map.put("p", "q");
        ThreadLocalMetadata.append(map);
        Assert.assertEquals("j", ThreadLocalMetadata.get("i"));
        Assert.assertEquals("q", ThreadLocalMetadata.get("p"));
    }


}
