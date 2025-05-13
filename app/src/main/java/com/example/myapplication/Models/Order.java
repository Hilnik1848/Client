package com.example.myapplication.Models;




    public class Order {
        private int id;
        private String name;
        private String avatar_name;
        private int id_category;
        private String id_profile;
        private String date;
        private Category category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar_name() {
            return avatar_name;
        }

        public void setAvatar_name(String avatar_name) {
            this.avatar_name = avatar_name;
        }

        public int getId_category() {
            return id_category;
        }

        public void setId_category(int id_category) {
            this.id_category = id_category;
        }

        public String getId_profile() {
            return id_profile;
        }

        public void setId_profile(String id_profile) {
            this.id_profile = id_profile;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }


