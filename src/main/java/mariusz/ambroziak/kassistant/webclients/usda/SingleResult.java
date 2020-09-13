package mariusz.ambroziak.kassistant.webclients.usda;

import mariusz.ambroziak.kassistant.enums.PhraseFoundDataSource;

public class SingleResult {
    private int fdcId;
    private String description;
    private String gtinUpc;
    private String dataType;


    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGtinUpc() {
        return gtinUpc;
    }

    public void setGtinUpc(String gtinUpc) {
        this.gtinUpc = gtinUpc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public PhraseFoundDataSource calculateType(){
        if(dataType.equalsIgnoreCase("SR Legacy")){
            return PhraseFoundDataSource.Usda_Legacy;
        }

        if(dataType.equalsIgnoreCase("Survey (FNDDS)")){
            return PhraseFoundDataSource.Usda_Survey;
        }


        if(dataType.equalsIgnoreCase("Foundation")){
            return PhraseFoundDataSource.Usda_Foundation;
        }


        if(dataType.equalsIgnoreCase("Branded")){
            return PhraseFoundDataSource.Usda_Branded;
        }

        return null;
    }
}
