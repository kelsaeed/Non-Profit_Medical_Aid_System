class ECG implements MedicalAidProgram {
    private final MedicalAidProgram program;

    public ECG(MedicalAidProgram program) {
        this.program = program;
    }

    @Override
    public String getDetails() {
        return program.getDetails() + " + ECG";
    }
}