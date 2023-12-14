package net.mceteams.functions;

import com.google.gson.*;
import org.bukkit.entity.Player;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class dataManagementFunction {
    private static final Logger Console = Logger.getLogger(dataManagementFunction.class.getName());
    public static void savePlayerData(Player p, String folderPath, String dataName, String data) {
        try {
            File folder = new File("plugins/" + "mce-smp" + folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, p.getName() + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(dataName, data); // Créer un objet JsonObject avec les données à sauvegarder.

            Gson gson = new Gson(); // Créer une instance de Gson.
            String jsonString = gson.toJson(jsonData); // Convertir l'objet JsonObject en une chaîne JSON.
            FileWriter writer = new FileWriter(file); // Créer une instance de FileWriter en utilisant le fichier spécifié.
            writer.write(jsonString); // Écrire la chaîne JSON dans le fichier.
            writer.close(); // Fermer le FileWriter.
            Console.info("Data sauvegarder correctement.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadPlayerData(Player p, String folderPath, String dataName) throws FileNotFoundException {
        File file = new File("plugins/" + "mce-smp" + folderPath, p.getName() + ".json");
        if (file.exists()) {

            FileReader reader = new FileReader(file);

            // Utiliser JsonParser pour analyser le fichier JSON
            JsonParser parser = new JsonParser();
            JsonObject jsonData = parser.parse(reader).getAsJsonObject();

            // Lire les données spécifiques à partir de l'objet JsonObject
            String data = jsonData.get(dataName).getAsString();

            // Fermer le FileReader
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return data;
        }
        return "false";
    }

    public static boolean deletePlayerData(Player p, String folderPath, String dataType) {
        File file = new File("plugins/" + "mce-smp" + folderPath, p.getName() + ".json");
        if (file.exists()) {
            if (dataType.equals("file")) {
                // Supprimer le fichier
                file.delete();
            } else if (dataType.equals("folder")) {
                // Supprimer le dossier
                file.getParentFile().delete();
            } else {
                // Supprimer une donnée spécifique
                try {
                    // Charger les données existantes du fichier JSON
                    JsonObject json = loadJSON(file);

                    // Supprimer la donnée spécifique du JSON
                    json.remove(dataType);

                    // Écrire les données mises à jour dans le fichier JSON
                    writeJSON(json, file);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public static List<String> getKeys(String playerName, String folderPath) {
        List<String> keys = new ArrayList<>();

        File folder = new File("plugins/mce-smp" + folderPath);
        if (folder.exists()) {
            File playerFile = new File(folder, playerName + ".json");
            if (playerFile.exists()) {
                try {
                    JsonObject jsonData = loadJSON(playerFile);
                    keys.addAll(jsonData.keySet());
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return keys;
    }

    private static JsonObject loadJSON(File file) throws IOException, ParseException {
        FileReader reader = new FileReader(file);
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(reader);
    }

    private static JsonObject loadJSONSecond(File file) throws IOException {
        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
        JsonParser parser = new JsonParser();
        return parser.parse(jsonContent).getAsJsonObject();
    }

    private static void writeJSON(JsonObject json, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    public static String searchParentFolder(String folderPath, String fileName) {
        File folder = new File("plugins/mce-smp/" + folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().equals(fileName + ".json")) {
                        return file.getParentFile().getName();
                    }
                }
            }
        }
        return null;
    }

    public static void saveData(String name, String folderPath, String dataName, String data) {
        try {
            File folder = new File("plugins/" + "mce-smp" + folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, name + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(dataName, data); // Créer un objet JsonObject avec les données à sauvegarder.

            Gson gson = new Gson(); // Créer une instance de Gson.
            String jsonString = gson.toJson(jsonData); // Convertir l'objet JsonObject en une chaîne JSON.
            FileWriter writer = new FileWriter(file); // Créer une instance de FileWriter en utilisant le fichier spécifié.
            writer.write(jsonString); // Écrire la chaîne JSON dans le fichier.
            writer.close(); // Fermer le FileWriter.
            Console.info("Data sauvegarder correctement.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadData(String name, String folderPath, String dataName) throws FileNotFoundException {
        File file = new File("plugins/" + "mce-smp" + folderPath, name + ".json");
        if (file.exists()) {

            FileReader reader = new FileReader(file);

            // Utiliser JsonParser pour analyser le fichier JSON
            JsonParser parser = new JsonParser();
            JsonObject jsonData = parser.parse(reader).getAsJsonObject();

            // Lire les données spécifiques à partir de l'objet JsonObject
            String data = jsonData.get(dataName).getAsString();

            // Fermer le FileReader
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return data;
        }
        return "false";
    }

    public static String searchLoadData(String folderPath, String dataName, String searchValue, String propertyToLoad) {
        File folder = new File("plugins/" + "mce-smp" + folderPath);
        if (folder.exists()) {
            try {
                List<File> files = (List<File>) FileUtils.listFiles(folder, null, true);

                for (File file : files) {
                    JsonObject jsonData = loadJSON(file);

                    if (jsonData.has(dataName)) {
                        String value = jsonData.get(dataName).getAsString();
                        if (value.equalsIgnoreCase(searchValue)) {
                            return loadData(file.getName().replace(".json", ""), folderPath, propertyToLoad);
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        return "false";
    }

    public static boolean doesDataExist(String name, String folderPath, String dataName) {
        File file = new File("plugins/mce-smp" + folderPath, name + ".json");
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);

                // Utiliser JsonParser pour analyser le fichier JSON
                JsonParser parser = new JsonParser();
                JsonObject jsonData = parser.parse(reader).getAsJsonObject();

                // Vérifier si la donnée existe dans l'objet JsonObject
                boolean dataExists = jsonData.has(dataName);

                // Fermer le FileReader
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return dataExists;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static void addData(String name, String folderPath, String dataName, String data) {
        try {
            File folder = new File("plugins/" + "mce-smp" + folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, name + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }

            JsonObject jsonData;
            if (file.length() > 0) {
                // Charger les données existantes du fichier JSON
                jsonData = loadJSON(file);
            } else {
                jsonData = new JsonObject();
            }

            // Ajouter la nouvelle donnée au JsonObject
            jsonData.addProperty(dataName, data);

            // Écrire les données mises à jour dans le fichier JSON
            writeJSON(jsonData, file);

            Console.info("Data ajoutée correctement.");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static String searchData(String folderPath, String dataName, String searchValue) {
        List<String> results = new ArrayList<>();

        File folder = new File("plugins/" + "mce-smp" + folderPath);
        if (folder.exists()) {
            try {
                List<File> files = (List<File>) FileUtils.listFiles(folder, null, true);

                for (File file : files) {
                    JsonObject jsonData = loadJSON(file);

                    if (jsonData.has(dataName)) {
                        String value = jsonData.get(dataName).getAsString();
                        if (value.equalsIgnoreCase(searchValue)) {
                            results.add(file.getName().replace(".json", ""));
                            return results.toString();
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String searchFoldersData(String folderPath, String dataName, String searchValue) {
        List<String> results = new ArrayList<>();

        File folder = new File("plugins/mce-smp/" + folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        try {
                            JsonObject jsonData = loadJSON(file);

                            if (jsonData.has(dataName)) {
                                String value = jsonData.get(dataName).getAsString();
                                if (value.equalsIgnoreCase(searchValue)) {
                                    results.add(file.getName().replace(".json", ""));
                                }
                            }
                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (!results.isEmpty()) {
            return results.toString();
        } else {
            return null;
        }
    }

    public static List<File> getEvery(String folderPath) {
        List<File> fileList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file);
                    } else if (file.isDirectory()) {
                        fileList.addAll(getEvery(file.getPath()));
                    }
                }
            }
        }

        return fileList;
    }

    public static List<String> getEveryData(String folderPath, String dataName) {
        List<String> dataList = new ArrayList<>();
        List<File> files = getEvery(folderPath);

        for (File file : files) {
            try {
                JsonObject jsonData = loadJSONSecond(file);

                if (jsonData.has(dataName)) {
                    JsonElement value = jsonData.get(dataName);
                    dataList.add(value.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    public static void deleteData(String name, String folderPath, String dataType) {
        File file = new File("plugins/" + "mce-smp" + folderPath, name + ".json");
        if (file.exists()) {
            if (dataType.equals("file")) {
                // Supprimer le fichier
                Console.info("tentative de suppression du fichier " + name);
                file.delete();
                if(file.exists()) {
                    Console.warning("Impossible de supprimer le fichiers.");
                }
            } else if (dataType.equals("folder")) {
                // Supprimer le dossier
                file.getParentFile().delete();
                if(file.exists()) {
                    Console.warning("Impossible de supprimer le fichiers.");
                }
            } else {
                // Supprimer une donnée spécifique
                try {
                    // Charger les données existantes du fichier JSON
                    JsonObject json = loadJSON(file);

                    // Supprimer la donnée spécifique du JSON
                    json.remove(dataType);

                    // Écrire les données mises à jour dans le fichier JSON
                    writeJSON(json, file);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
