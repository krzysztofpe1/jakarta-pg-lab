package krzysztof.pecyna.eventsViewer.performance.model.function;

import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceModel;
import krzysztof.pecyna.eventsViewer.performance.model.PerformancesModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class PerformancesToModelFunction implements Function<List<Performance>, PerformancesModel> {

    @Override
    public PerformancesModel apply(List<Performance> performances) {
        return PerformancesModel.builder()
                .performances(performances.stream()
                        .map(performance -> PerformancesModel.Performance.builder()
                                .id(performance.getId())
                                .date(performance.getDate())
                                .performanceType(performance.getPerformanceType())
                                .version(performance.getVersion())
                                .creationDateTime(performance.getCreationDateTime())
                                .modifiedDateTime(performance.getEditDateTime())
                                .build())
                        .toList()
                )
                .build();
    }
}
