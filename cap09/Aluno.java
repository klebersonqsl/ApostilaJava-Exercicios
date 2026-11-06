// Comparable define a "ordem natural" dos alunos: por nome
public class Aluno implements Comparable<Aluno> {
    private final String nome;
    private final String curso;
    private final double media;

    public Aluno(String nome, String curso, double media) {
        this.nome = nome;
        this.curso = curso;
        this.media = media;
    }

    public String getNome() { return nome; }
    public String getCurso() { return curso; }
    public double getMedia() { return media; }

    @Override
    public int compareTo(Aluno outro) {
        return this.nome.compareTo(outro.nome);
    }

    @Override
    public String toString() {
        return String.format("%-8s %-9s %4.1f", nome, curso, media);
    }
}
