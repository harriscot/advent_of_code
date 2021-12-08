package main.java.prob1;

import java.util.List;

public class DepthGauge {
    DepthDataReader depthDataReader = new DepthDataReader();

    public void countDepthIncreases() {
        List<Integer> depths = depthDataReader.getDepthData();
        Integer increases = countIncreases(depths);
        System.out.println("Increases: " + increases);
    }

    private Integer countIncreases(List<Integer> depths) {
        int increases = 0;
        int lastDepth = 0;
        for(Integer depth: depths){
            if(depth > lastDepth) {
                increases ++;
            }
            lastDepth = depth;
        }
        return increases - 1;
    }
}
