package springWeb.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatEngineTest {
    @Test
    public void should_set_config_file() {
        String originConfigFile = "config file";

        ChatEngine chatEngine = new ChatEngine(originConfigFile);

        assertThat(chatEngine.configFile(), is(originConfigFile));

    }

    @Test
    public void should_(){
        String updatedConfigFile = "updated config file";

        ChatEngine chatEngine1 = new ChatEngine(updatedConfigFile);


        assertThat(chatEngine1.configFile(), is(updatedConfigFile));
    }
}
