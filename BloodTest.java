public class BloodTest extends MedicalAidDecorator{
    MedicalAidProgram map;

    public BloodTest(MedicalAidProgram map){
        this.map=map;
    }
    @Override
    public String MedicalType() {
        return(map.MedicalType() + " + BloodTest");
    }
}
