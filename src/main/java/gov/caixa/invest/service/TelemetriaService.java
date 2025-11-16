package gov.caixa.invest.service;
import gov.caixa.invest.dto.TelemetriaResponse;
import gov.caixa.invest.entity.TelemetriaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TelemetriaService {

    @Transactional
    public void registrarExecucao(String nomeServico, long duracaoMs) {
        LocalDate hoje = LocalDate.now();
        TelemetriaEntity entity = TelemetriaEntity.find("nomeServico = ?1 and dataRegistro = ?2", nomeServico, hoje).firstResult();

        if (entity == null) {
            entity = new TelemetriaEntity();
            entity.setNomeServico(nomeServico);
            entity.setQuantidadeChamadas(1L);
            entity.setMediaTempoRespostaMs((double) duracaoMs);
            entity.setDataRegistro(hoje);
            entity.persist();
        } else {
            long quantidadeAtual = entity.getQuantidadeChamadas();
            double mediaAtual = entity.getMediaTempoRespostaMs();
            double novaMedia = ((mediaAtual * quantidadeAtual) + duracaoMs) / (quantidadeAtual + 1);

            entity.setQuantidadeChamadas(quantidadeAtual + 1);
            entity.setMediaTempoRespostaMs(novaMedia);
        }
    }

    public List<TelemetriaResponse> listar() {
        return TelemetriaEntity.<TelemetriaEntity>listAll().stream().map(this::mapearResposta).toList();
    }

    private TelemetriaResponse mapearResposta(TelemetriaEntity entity) {
        TelemetriaResponse response = new TelemetriaResponse();
        response.nomeServico = entity.getNomeServico();
        response.quantidadeChamadas = entity.getQuantidadeChamadas();
        response.mediaTempoRespostaMs = entity.getMediaTempoRespostaMs();
        response.dataRegistro = entity.getDataRegistro();
        return response;
    }
}