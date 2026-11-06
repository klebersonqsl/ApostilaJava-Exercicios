import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Datas {
    public static void main(String[] args) {
        Locale ptBR = Locale.of("pt", "BR");
        LocalDate nascimento = LocalDate.of(1998, 7, 23);
        LocalDate ref = LocalDate.of(2026, 10, 26);    // data fixa para o exemplo
        // LocalDate.now() devolveria a data de hoje

        DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter extenso =
                DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", ptBR);

        System.out.println("Nascimento: " + nascimento.format(br));
        System.out.println("Referência: " + ref.format(extenso));

        Period p = Period.between(nascimento, ref);
        System.out.printf("Idade: %d anos, %d meses e %d dias%n",
                p.getYears(), p.getMonths(), p.getDays());

        // somar, subtrair e consultar
        LocalDate venc = ref.plusDays(30);
        DayOfWeek dia = venc.getDayOfWeek();
        System.out.println("Vencimento: " + venc.format(br) + " ("
                + dia.getDisplayName(TextStyle.FULL, ptBR) + ")");
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            System.out.println("Cai no fim de semana!");
        }
        LocalDate ultimoDia = ref.withDayOfMonth(ref.lengthOfMonth());
        System.out.println("Último dia do mês: " + ultimoDia.format(br));
        long dias = ChronoUnit.DAYS.between(ref, LocalDate.of(2026, 12, 25));
        System.out.println("Dias até o Natal: " + dias);

        // texto -> data
        LocalDate lida = LocalDate.parse("15/11/2026", br);
        System.out.println("Lida: " + lida + " | depois da ref.? " + lida.isAfter(ref));

        // horas e duração
        LocalTime entrada = LocalTime.of(8, 30);
        LocalTime saida = LocalTime.of(17, 45);
        Duration jornada = Duration.between(entrada, saida);
        System.out.println("Jornada: " + jornada.toHours() + "h"
                + jornada.toMinutesPart() + "min");

        // data com hora e fusos horários
        LocalDateTime reuniao = LocalDateTime.of(2026, 11, 3, 14, 0);
        DateTimeFormatter dh = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        System.out.println("Reunião em São Paulo: " + reuniao.format(dh));
        ZonedDateTime sp = reuniao.atZone(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime lisboa = sp.withZoneSameInstant(ZoneId.of("Europe/Lisbon"));
        System.out.println("Em Lisboa será: " + lisboa.format(dh));
    }
}
