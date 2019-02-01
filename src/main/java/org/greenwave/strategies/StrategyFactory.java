package org.greenwave.strategies;

import org.greenwave.model.DataDomain;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class StrategyFactory {
	
	private Map<DataDomain, Strategy> strategies = new EnumMap<>(DataDomain.class);

    public StrategyFactory() {
        initStrategies();
    }

    public Strategy getStrategy(DataDomain dataDomain) {
        if (dataDomain == null || !strategies.containsKey(dataDomain)) {
            throw new IllegalArgumentException("Invalid " + dataDomain);
        }
        return strategies.get(dataDomain);
    }

    private void initStrategies() {
        strategies.put(DataDomain.EARTH, new StrategyOperationEarth());
        strategies.put(DataDomain.WATER, new StrategyOperationWater());
        strategies.put(DataDomain.AIR, new StrategyOperationAir());
    }

}
