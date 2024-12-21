package org.example.data.core.config;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ConfigYaml {
    private Map<String, Map<String, String>> repositories;
    public ConfigYaml(){
        loadConfig();
    }
    private void loadConfig(){
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/config.yaml")) {
            if (inputStream == null) {
                throw new IllegalStateException("Le fichier config.yaml est introuvable.");
            }
            repositories = yaml.load(inputStream);
            if (repositories == null) {
                throw new IllegalStateException("Le fichier YAML a été chargé, mais il est vide ou mal formaté.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getRepositoryType(String entity) {
        Map<String, String> entityConfig = repositories.get(entity);
        if (entityConfig == null) {
            throw new IllegalArgumentException("L'entité '" + entity + "' n'est pas configurée dans le fichier YAML.");
        }
        return entityConfig.get("type");
    }
    
}
