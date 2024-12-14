package krzysztof.pecyna.eventsViewer.performance.model.function;

import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceModel;

import java.io.Serializable;
import java.util.function.Function;

public class PerformanceToModelFunction implements Function<Performance, PerformanceModel>, Serializable {

    @Override
    public PerformanceModel apply(Performance performance) {
        return PerformanceModel.builder()
                .id(performance.getId())
                .date(performance.getDate())
                .performanceType(performance.getPerformanceType())
                .version(performance.getVersion())
                .creationDateTime(performance.getCreationDateTime())
                .build();
    }
}
