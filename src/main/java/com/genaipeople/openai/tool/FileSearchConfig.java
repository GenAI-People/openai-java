package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileSearchConfig {
    @JsonProperty("max_num_results")
    private Integer maxNumResults;

    @JsonProperty("ranking_options")
    private RankingOptions rankingOptions;

    public Integer getMaxNumResults() {
        return maxNumResults;
    }

    public void setMaxNumResults(Integer maxNumResults) {
        if (maxNumResults != null && (maxNumResults < 1 || maxNumResults > 50)) {
            throw new IllegalArgumentException("Max number of results must be between 1 and 50");
        }
        this.maxNumResults = maxNumResults;
    }

    public RankingOptions getRankingOptions() {
        return rankingOptions;
    }

    public void setRankingOptions(RankingOptions rankingOptions) {
        this.rankingOptions = rankingOptions;
    }

    public class RankingOptions {
        @JsonProperty("ranker")
        private String ranker;

        @JsonProperty("score_threshold")
        private Double scoreThreshold;

        public RankingOptions(Double scoreThreshold) {
            this.scoreThreshold = scoreThreshold;
        }

        public String getRanker() {
            return ranker;
        }

        public void setRanker(String ranker) {
            this.ranker = ranker;
        }

        public Double getScoreThreshold() {
            return scoreThreshold;
        }

        public void setScoreThreshold(Double scoreThreshold) {
            if (scoreThreshold < 0 || scoreThreshold > 1) {
                throw new IllegalArgumentException("Score threshold must be between 0 and 1");
            }
            this.scoreThreshold = scoreThreshold;
        }
    }
}
