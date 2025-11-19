package gov.caixa.invest.telemetria;

import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@MedirTelemetria("")
@Priority(Interceptor.Priority.APPLICATION)
@Interceptor
public class TelemetriaInterceptor {

    @Inject
    TelemetriaService telemetriaService;

    @AroundInvoke
    public Object registrar(InvocationContext ctx) throws Exception {
        MedirTelemetria binding = obterBinding(ctx);
        long inicio = System.nanoTime();
        try {
            return ctx.proceed();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao(binding.value(), duracaoMs);
        }
    }

    private MedirTelemetria obterBinding(InvocationContext ctx) {
        MedirTelemetria metodo = ctx.getMethod().getAnnotation(MedirTelemetria.class);
        if (metodo != null && !metodo.value().isBlank()) {
            return metodo;
        }
        MedirTelemetria classe = ctx.getTarget().getClass().getAnnotation(MedirTelemetria.class);
        if (classe != null && !classe.value().isBlank()) {
            return classe;
        }
        throw new IllegalStateException("Medir Telemetria sem identificador configurado");
    }
}