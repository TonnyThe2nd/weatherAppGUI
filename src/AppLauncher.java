import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //iniciar aplicação
                new WeatherAppGUI().setVisible(true);
                System.out.println(WeatherApp.getWeatherData("United States"));
            }
        });
    }
}
