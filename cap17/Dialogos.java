import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Dialogos {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        String nome = JOptionPane.showInputDialog(null, "Qual é o seu nome?",
                "Identificação", JOptionPane.QUESTION_MESSAGE);
        if (nome == null || nome.isBlank()) {
            return;                                           // clicou em Cancelar
        }

        int resp = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?",
                "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);
        String escolha = switch (resp) {
            case JOptionPane.YES_OPTION -> "Sim";
            case JOptionPane.NO_OPTION -> "Não";
            default -> "Cancelar";
        };

        String[] opcoes = {"Pix", "Cartão", "Boleto"};
        Object forma = JOptionPane.showInputDialog(null, "Forma de pagamento:",
                "Pagamento", JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        JOptionPane.showMessageDialog(null,
                "Olá, " + nome + "!\nVocê escolheu: " + escolha
                        + "\nPagamento: " + forma,
                "Resumo", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(null, "Arquivo não encontrado.", "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}
