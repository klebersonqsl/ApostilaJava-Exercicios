import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/** Relógio digital e cronômetro com javax.swing.Timer. */
public class RelogioSwing {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATA =
            DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy",
                    Locale.of("pt", "BR"));

    private final JLabel hora = new JLabel("", SwingConstants.CENTER);
    private final JLabel data = new JLabel("", SwingConstants.CENTER);
    private final JLabel cronometro = new JLabel("00:00.0", SwingConstants.CENTER);

    private long decimos;                // tempo do cronômetro, em décimos de segundo

    // o relógio "bate" a cada 1 s; o cronômetro, a cada 100 ms
    private final Timer timerRelogio = new Timer(1000, e -> atualizarRelogio());
    private final Timer timerCronometro = new Timer(100, e -> {
        decimos++;
        Duration d = Duration.ofMillis(decimos * 100);
        cronometro.setText(String.format("%02d:%02d.%d",
                d.toMinutes(), d.toSecondsPart(), d.toMillisPart() / 100));
    });

    private void montar() {
        JFrame janela = new JFrame("Relógio");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hora.setFont(new Font(Font.MONOSPACED, Font.BOLD, 56));
        hora.setForeground(new Color(0x1B3A5C));
        data.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        cronometro.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        cronometro.setForeground(new Color(0xD2640A));

        JButton iniciar = new JButton("Iniciar");
        JButton pausar = new JButton("Pausar");
        JButton zerar = new JButton("Zerar");
        iniciar.addActionListener(e -> timerCronometro.start());
        pausar.addActionListener(e -> timerCronometro.stop());
        zerar.addActionListener(e -> {
            timerCronometro.stop();
            decimos = 0;
            cronometro.setText("00:00.0");
        });

        JPanel topo = new JPanel(new GridLayout(2, 1));
        topo.add(hora);
        topo.add(data);
        JPanel botoes = new JPanel();
        botoes.add(iniciar);
        botoes.add(pausar);
        botoes.add(zerar);
        JPanel crono = new JPanel(new BorderLayout());
        crono.setBorder(BorderFactory.createTitledBorder("Cronômetro"));
        crono.add(cronometro, BorderLayout.CENTER);
        crono.add(botoes, BorderLayout.SOUTH);

        JPanel conteudo = new JPanel(new BorderLayout(0, 12));
        conteudo.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        conteudo.add(topo, BorderLayout.CENTER);
        conteudo.add(crono, BorderLayout.SOUTH);
        janela.setContentPane(conteudo);

        atualizarRelogio();                     // mostra a hora já, sem esperar 1 s
        timerRelogio.start();
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    private void atualizarRelogio() {
        LocalDateTime agora = LocalDateTime.now();
        hora.setText(agora.format(HORA));
        data.setText(agora.format(DATA));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RelogioSwing().montar());
    }
}
