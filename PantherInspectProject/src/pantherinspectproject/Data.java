/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

/**
 *
 * @author cindy
 */
public class Data {

        private String datePosted;
        private String name;
        private String avgRating;


        public Data(String datePosted, String name, String avgRating) {
            this.datePosted = datePosted;
            this.name = name;
            this.avgRating = avgRating;
        }

        public String getDatePosted() {
            return datePosted;
        }

        public void setDatePosted(String dp) {
            this.datePosted = dp;
        }


        public String getName() {
            return name;
        }

        public void setName(String nme) {
            this.name = nme;
        }

        public String getAvgRating() {
            return avgRating;

        }

        public void setAvgRatings(String sr) {
            this.avgRating = sr;
        }


        @Override
        public String toString() {
            return "date posted:" + datePosted + " name: " + name + " stars: " + avgRating;
        }

    }
