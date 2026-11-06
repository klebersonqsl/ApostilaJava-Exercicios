import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Calculadora extends JFrame {

    private final JTextField visor = new JTextField("0");
    private BigDecimal acumulado = BigDecimal.ZERO;
    private String operacao = "";
    private boolean novoNumero = true;

    public Calculadora() {
        super("Calculadora");
        visor.setEditable(false);
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));

        JPanel teclas = new JPanel(new GridLayout(5, 4, 4, 4));
        String[] rotulos = {"C", "±", "%", "/", "7", "8", "9", "*", "4", "5", "6", "-",
                            "1", "2", "3", "+", "0", "00", ",", "="};
        for (String r : rotulos) {
            JButton b = new JButton(r);
            b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            b.addActionListener(e -> tecla(r));      // um só método trata todos
            teclas.add(b);
        }

        JPanel raiz = new JPanel(new BorderLayout(6, 6));
        raiz.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        raiz.add(visor, BorderLayout.NORTH);
        raiz.add(teclas, BorderLayout.CENTER);
        add(raiz);
        setSize(280, 330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void tecla(String t) {
        switch (t) {
            case "C" -> {
                visor.setText("0");
                acumulado = BigDecimal.ZERO;
                operacao = "";
                novoNumero = true;
            }
            case "±" -> mostrar(atual().negate());
            case "%" -> mostrar(atual().divide(BigDecimal.valueOf(100)));
            case "+", "-", "*", "/" -> {
                calcularPendente();
                operacao = t;
                novoNumero = true;
            }
            case "=" -> {
                calcularPendente();
                operacao = "";
                novoNumero = true;
            }
            case "," -> {
                if (novoNumero) {
                    visor.setText("0,");
                    novoNumero = false;
                } else if (!visor.getText().contains(",")) {
                    visor.setText(visor.getText() + ",");
                }
            }
            default -> {                              // dígitos
                if (novoNumero || visor.getText().equals("0")) {
                    visor.setText(t.equals("00") ? "0" : t);
                    novoNumero = false;
                } else {
                    visor.setText(visor.getText() + t);
                }
            }
        }
    }

    private void calcularPendente() {
        BigDecimal valor = atual();
        acumulado = switch (operacao) {
            case "+" -> acumulado.add(valor);
            case "-" -> acumulado.subtract(valor);
            case "*" -> acumulado.multiply(valor);
            case "/" -> valor.signum() == 0 ? null
                    : acumulado.divide(valor, 10, RoundingMode.HALF_EVEN);
            default -> valor;
        };
        if (acumulado == null) {
            visor.setText("Erro");
            acumulado = BigDecimal.ZERO;
        } else {
            mostrar(acumulado);
        }
    }

    private BigDecimal atual() {
        try {
            return new BigDecimal(visor.getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private void mostrar(BigDecimal v) {
        visor.setText(v.stripTrailingZeros().toPlainString().replace('.', ','));
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}
