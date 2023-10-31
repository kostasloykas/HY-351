package mainClasses;

public class Nurse extends MedicalStaff{
    int nurse_id,doctor_id,hospital_id;
    String nurse_info;

    public int getNurse_id() {
        return nurse_id;
    }

    public void setNurse_id(int nurse_id) {
        this.nurse_id = nurse_id;
    }

    public String getNurse_info() {
        return nurse_info;
    }

    public void setNurse_info(String nurse_info) {
        this.nurse_info = nurse_info;
    }
    
    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
    
    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

  
    
}
