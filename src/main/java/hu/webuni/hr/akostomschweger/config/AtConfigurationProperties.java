package hu.webuni.hr.akostomschweger.config;

import org.springframework.stereotype.Component;


@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "hr")
@Component
public class AtConfigurationProperties {

    private Salary salary = new Salary();

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public static class Salary {
        private Default def = new Default();
        private Special special = new Special();


        public Default getDef() {
            return def;
        }

        public void setDef(Default def) {
            this.def = def;
        }

        public Special getSpecial() {
            return special;
        }

        public void setSpecial(Special special) {
            this.special = special;
        }
    }

    public static class Default {
        private int percent;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }



    public static class Special {

        private int percent1;
        private int percent2;
        private int percent3;
        private int percent4;
        private double limit1;
        private double limit2;
        private double limit3;

        public int getPercent1() {
            return percent1;
        }

        public void setPercent1(int percent1) {
            this.percent1 = percent1;
        }

        public int getPercent2() {
            return percent2;
        }

        public void setPercent2(int percent2) {
            this.percent2 = percent2;
        }

        public int getPercent3() {
            return percent3;
        }

        public void setPercent3(int percent3) {
            this.percent3 = percent3;
        }

        public int getPercent4() {
            return percent4;
        }

        public void setPercent4(int percent4) {
            this.percent4 = percent4;
        }

        public double getLimit1() {
            return limit1;
        }

        public void setLimit1(double limit1) {
            this.limit1 = limit1;
        }

        public double getLimit2() {
            return limit2;
        }

        public void setLimit2(double limit2) {
            this.limit2 = limit2;
        }

        public double getLimit3() {
            return limit3;
        }

        public void setLimit3(double limit3) {
            this.limit3 = limit3;
        }
    }

}
