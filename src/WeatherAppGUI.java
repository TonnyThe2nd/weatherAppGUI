import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import org.json.simple.JSONObject;

public class WeatherAppGUI extends JFrame {

    private JSONObject weatherData;

    public WeatherAppGUI(){
        //título da aplicação
        super("Weather Gui");
        //encerrar a aplicação ao fechar o frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //tamanho do frame
        setSize(450,650);
        //abrir o frame no centro da tela
        setLocationRelativeTo(null);
        //mudar o layout para 'null' e organizar melhor os componentes
        setLayout(null);
        //desabilitar a mudança de  tamanhos
        setResizable(false);
        //adicionando componentes ao frame
        addComponents();
    }
    //método de componentes
    private void addComponents(){
        //ADICIONANDO CAMPO DE PESQUISA
        //campo de pesquisa
        JTextField searchTextField = new JTextField();
        //configurando textfield
        searchTextField.setBounds(15,15,351,45);
        //estilizando fonte
        searchTextField.setFont(new Font("Dialog", Font.BOLD,24));
        //adicionando a textfield ao frame principal
        add(searchTextField);

        //ADICIONANDO IMAGEM DE CLIMA
        //imagem
        JLabel weatherImage = new JLabel(loadImage("src/assets/cloudy.png"));
        //tamanho da imagem
        weatherImage.setBounds(0,125,450,217);
        //adicionando imagem ao frame principal
        add(weatherImage);

        //ADICIONANDO TEXTO DE TEMPERATURA
        //texto
        JLabel temperature = new JLabel("30º C");
        //configuranto texto
        temperature.setBounds(0,350,450,54);
        temperature.setFont(new Font("Dialog",Font.BOLD,48));
        //centralizando texto
        temperature.setHorizontalAlignment(SwingConstants.CENTER);
        //adicionando texto de temperatura ao frame principal
        add(temperature);

        //descrição de clima
        JLabel weatherCondition = new JLabel("Cloudy");
        //configurando texto
        weatherCondition.setBounds(0,405,450,36);
        weatherCondition.setFont(new Font("Dialog",Font.PLAIN,32));
        //centralizando texto
        weatherCondition.setHorizontalAlignment(SwingUtilities.CENTER);
        //adicionando texto ao frame principal
        add(weatherCondition);

        //UMIDADE
        //imagem
        JLabel humidity = new JLabel(loadImage("src/assets/humidity.png"));
        //configurando imagem
        humidity.setBounds(15,500,74,66);
        add(humidity);
        //texto
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        //VENTO
        //imagem
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        //configuranto imagem
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);
        //texto
        JLabel windSpeedText = new JLabel("<html><b>WindSpeed</b> 35km/H</html");
        windSpeedText.setBounds(310,500,89,55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windSpeedText);

        //ADICIONANDO BOTÃO
        //botão de pesquisa
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        //mudando cursor no botão
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //tamanho do botão
        searchButton.setBounds(375,13,47,45);
        //adicionando botão ao frame principal
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtendo localização do usuário
                String userInput = searchTextField.getText();

                // validando texto
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }
                weatherData = WeatherApp.getWeatherData(userInput);
                // atualizando o clima

                // atualizando a imagem em relação ao clima
                String weatherConditionUpdate = (String) weatherData.get("weather_condition");

                // condição para imagem que será atualizada
                switch(weatherConditionUpdate){
                    case "Clear":
                        weatherImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }
                // atualizando texto da temperatura
                double newTemperature = (double) weatherData.get("temperature");
                temperature.setText(newTemperature + "º C");

                weatherCondition.setText(weatherConditionUpdate);

                // atualizando texto da umidade
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // atualizando texto da velocidade do vento
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String sourcePath){
        try{
            //carregar imagem escolhida através do seu caminho
            BufferedImage image = ImageIO.read(new File(sourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Not able to open Image!");
        return null;
    }
}
