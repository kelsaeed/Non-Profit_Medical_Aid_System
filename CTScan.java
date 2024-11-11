public class CTScan extends MedicalAidDecorator {
    MedicalAidProgram map;

    public CTScan(MedicalAidProgram map){
        this.map=map;
    }

    @Override
    public String MedicalType() {
        return(map.MedicalType() + " + CTScan");
    }
}
