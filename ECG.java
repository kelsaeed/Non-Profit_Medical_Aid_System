public class ECG extends MedicalAidDecorator {
    MedicalAidProgram map;

    public ECG(MedicalAidProgram map){
        this.map=map;
    }

    @Override
    public String MedicalType() {
        return(map.MedicalType() + " + ECG");
    }
}
