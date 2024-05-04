package com.microservices.projectfinal.security;

public enum PermissionType {

        STUDENT("STUDENT"), TUTOR("TUTOR"), ADMIN("ADMIN");

        private final String type;

        PermissionType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
}
