
class CTScan implements MedicalAidProgram {
    private final MedicalAidProgram program;

    public CTScan(MedicalAidProgram program) {
        this.program = program;
    }

    @Override
    public String getDetails() {
        return program.getDetails() + " + CT Scan";
    }
}