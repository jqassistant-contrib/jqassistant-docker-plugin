package org.jqassistant.contrib.plugin.docker.rest;

import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import lombok.extern.slf4j.Slf4j;
import org.jqassistant.contrib.plugin.docker.api.model.DockerRegistryDescriptor;
import org.jqassistant.contrib.plugin.docker.api.scope.DockerScope;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DockerRemoteRegistryScannerPluginIT extends AbstractPluginIT {

    @Disabled("Only to be executed manually, not in CI build.")
    @Test
    @TestStore(type = TestStore.Type.FILE)
    void scanLocalRegistry() throws MalformedURLException {
        DockerRegistryDescriptor registryDescriptor = scanRegistry("http://localhost:5000", "*");
        assertThat(registryDescriptor).isNotNull();
    }

    private DockerRegistryDescriptor scanRegistry(String url, String repositoryIncludePattern) throws MalformedURLException {
        return scanRegistry(url, repositoryIncludePattern, null);
    }

    private DockerRegistryDescriptor scanRegistry(String url, String repositoryIncludePattern, String repositoryExcludePattern)
        throws MalformedURLException {
        Map<String, Object> props = new HashMap<>();
        props.put("docker.repository.include", repositoryIncludePattern);
        if (repositoryExcludePattern != null) {
            props.put("docker.repository.exclude", repositoryExcludePattern);
        }
        return getScanner(props).scan(new URL(url), url, DockerScope.REGISTRY);
    }
}
