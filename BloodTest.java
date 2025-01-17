class BloodTest implements MedicalAidProgram {
    private final MedicalAidProgram program;

    public BloodTest(MedicalAidProgram program) {
        this.program = program;
    }

    @Override
    public String getDetails() {
        return program.getDetails() + " + Blood Test";
    }
}