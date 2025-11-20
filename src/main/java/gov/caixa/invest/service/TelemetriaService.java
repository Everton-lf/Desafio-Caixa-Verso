package gov.caixa.invest.service;

import gov.caixa.invest.dto.TelemetriaListaResponse;
import gov.caixa.invest.dto.TelemetriaPeriodoResponse;
import gov.caixa.invest.dto.TelemetriaServicoResponse;
import gov.caixa.invest.entity.Telemetria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TelemetriaService {

    @Transactional
    public void registrarExecucao(String nomeServico, long duracaoMs) {
        LocalDate hoje = LocalDate.now();
        Telemetria entity = Telemetria.find("nomeServico = ?1 and dataRegistro = ?2", nomeServico, hoje).firstResult();

        if (entity == null) {
            entity = new Telemetria();
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

    public TelemetriaListaResponse listar() {
        List<Telemetria> registros = Telemetria.listAll();

        TelemetriaListaResponse resposta = new TelemetriaListaResponse();
        resposta.servicos = registros.stream().map(this::mapearServico).toList();
        resposta.periodo = mapearPeriodo(registros);

        return resposta;
    }

        private TelemetriaServicoResponse mapearServico(Telemetria entity){
            TelemetriaServicoResponse response = new TelemetriaServicoResponse();
            response.nome = entity.getNomeServico();
            response.quantidadeChamadas = entity.getQuantidadeChamadas();
            response.mediaTempoRespostaMs = entity.getMediaTempoRespostaMs();
            return response;
        }
        private TelemetriaPeriodoResponse mapearPeriodo(List<Telemetria> registros) {
            TelemetriaPeriodoResponse periodo = new TelemetriaPeriodoResponse();

            if (!registros.isEmpty()) {
                LocalDate dataMaisRecente = registros.stream()
                        .map(Telemetria::getDataRegistro)
                        .max(LocalDate::compareTo)
                        .orElse(null);

                LocalDate dataMaisAntiga = registros.stream()
                        .map(Telemetria::getDataRegistro)
                        .min(LocalDate::compareTo)
                        .orElse(null);

                periodo.inicio = dataMaisAntiga;
                periodo.fim = dataMaisRecente;
            }

            return periodo;
        }
    }
