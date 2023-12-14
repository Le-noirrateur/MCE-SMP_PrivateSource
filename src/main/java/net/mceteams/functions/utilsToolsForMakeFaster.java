package net.mceteams.functions;

public class utilsToolsForMakeFaster {
    public static void wait(int Time) {
        try {
            // Suspend l'exécution pendant 2 secondes (2000 millisecondes)
            Thread.sleep(Time * 1000);
        } catch (InterruptedException e) {
            // Gérer l'exception si elle se produit
            e.printStackTrace();
        }

    }
}
