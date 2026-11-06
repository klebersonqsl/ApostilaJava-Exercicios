import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CalculadoraImc extends JFrame {

    private final JTextField campoPeso = new JTextField(8);
    private final JTextField campoAltura = new JTextField(8);
    private final JLabel resultado =
            new JLabel("Informe peso e altura", SwingConstants.CENTER);

    public CalculadoraImc() {
        super("Calculadora de IMC");

        JPanel campos = new JPanel(new GridLayout(2, 2, 6, 6));
        campos.add(new JLabel("Peso (kg):"));
        campos.add(campoPeso);
        campos.add(new JLabel("Altura (m):"));
        campos.add(campoAltura);

        JButton calcular = new JButton("Calcular");
        calcular.addActionListener(e -> calcular());     // o que fazer ao clicar
        campoAltura.addActionListener(e -> calcular());  // Enter no campo também calcula

        resultado.setFont(resultado.getFont().deriveFont(Font.BOLD, 15f));

        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        raiz.add(campos, BorderLayout.NORTH);
        raiz.add(calcular, BorderLayout.CENTER);
        raiz.add(resultado, BorderLayout.SOUTH);
        add(raiz);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void calcular() {
        try {
            double peso = Double.parseDouble(campoPeso.getText().replace(',', '.'));
            double altura = Double.parseDouble(campoAltura.getText().replace(',', '.'));
            double imc = peso / (altura * altura);
            String faixa = imc < 18.5 ? "abaixo do peso" : imc < 25 ? "peso normal"
                    : imc < 30 ? "sobrepeso" : "obesidade";
            resultado.setText(String.format("IMC %.1f: %s", imc, faixa));
            boolean normal = imc >= 18.5 && imc < 25;
            resultado.setForeground(normal ? new Color(0, 120, 0) : Color.RED);
        } catch (NumberFormatException ex) {
            resultado.setText("Digite números válidos");
            resultado.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> new CalculadoraImc().setVisible(true));
    }
}
