/*
 Navicat Premium Data Transfer

 Source Server         : local-postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 160002 (160002)
 Source Host           : localhost:5432
 Source Catalog        : tutor-system
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160002 (160002)
 File Encoding         : 65001

 Date: 23/05/2024 01:50:18
*/


-- ----------------------------
-- Sequence structure for accounts_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."accounts_id_seq";
CREATE SEQUENCE "public"."accounts_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for availabilities_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."availabilities_id_seq";
CREATE SEQUENCE "public"."availabilities_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for booking_purchases_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."booking_purchases_id_seq";
CREATE SEQUENCE "public"."booking_purchases_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for bookings_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."bookings_id_seq";
CREATE SEQUENCE "public"."bookings_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for course_enrollments_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."course_enrollments_id_seq";
CREATE SEQUENCE "public"."course_enrollments_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for course_purchases_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."course_purchases_id_seq";
CREATE SEQUENCE "public"."course_purchases_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for course_ratings_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."course_ratings_id_seq";
CREATE SEQUENCE "public"."course_ratings_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for course_videos_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."course_videos_id_seq";
CREATE SEQUENCE "public"."course_videos_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for courses_course_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."courses_course_id_seq";
CREATE SEQUENCE "public"."courses_course_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for payment_logs_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."payment_logs_id_seq";
CREATE SEQUENCE "public"."payment_logs_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for payment_transactions_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."payment_transactions_id_seq";
CREATE SEQUENCE "public"."payment_transactions_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for payments_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."payments_id_seq";
CREATE SEQUENCE "public"."payments_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tutors_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tutors_id_seq";
CREATE SEQUENCE "public"."tutors_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS "public"."accounts";
CREATE TABLE "public"."accounts" (
  "id" int8 NOT NULL DEFAULT nextval('accounts_id_seq'::regclass),
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "avatar_path" varchar(255) COLLATE "pg_catalog"."default",
  "birth" timestamptz(6),
  "created_at" timestamp(6) NOT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "firstname" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "gender" varchar(255) COLLATE "pg_catalog"."default",
  "lastname" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "modified_at" timestamp(6) NOT NULL,
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for availabilities
-- ----------------------------
DROP TABLE IF EXISTS "public"."availabilities";
CREATE TABLE "public"."availabilities" (
  "id" int8 NOT NULL DEFAULT nextval('availabilities_id_seq'::regclass),
  "created_at" timestamp(6) NOT NULL,
  "day_of_week" int4 NOT NULL,
  "end_time" timestamp(6) NOT NULL,
  "is_active" bool NOT NULL DEFAULT true,
  "modified_at" timestamp(6) NOT NULL,
  "start_time" timestamp(6) NOT NULL,
  "tutor_id" int8
)
;

-- ----------------------------
-- Table structure for booking_purchases
-- ----------------------------
DROP TABLE IF EXISTS "public"."booking_purchases";
CREATE TABLE "public"."booking_purchases" (
  "id" int4 NOT NULL DEFAULT nextval('booking_purchases_id_seq'::regclass),
  "created_at" timestamp(6) NOT NULL,
  "modified_at" timestamp(6) NOT NULL,
  "payment_id" int4 NOT NULL,
  "purchase_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "booking_id" int8 NOT NULL,
  "user_id" int8 NOT NULL
)
;

-- ----------------------------
-- Table structure for bookings
-- ----------------------------
DROP TABLE IF EXISTS "public"."bookings";
CREATE TABLE "public"."bookings" (
  "id" int8 NOT NULL DEFAULT nextval('bookings_id_seq'::regclass),
  "booking_time" timestamp(6) NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "availability_id" int8,
  "student_id" int8
)
;

-- ----------------------------
-- Table structure for course_enrollments
-- ----------------------------
DROP TABLE IF EXISTS "public"."course_enrollments";
CREATE TABLE "public"."course_enrollments" (
  "id" int8 NOT NULL DEFAULT nextval('course_enrollments_id_seq'::regclass),
  "created_at" timestamptz(6) NOT NULL,
  "enrollment_date" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modified_at" timestamptz(6) NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default" DEFAULT 'inactive'::character varying,
  "student_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "course_id" int8 NOT NULL
)
;

-- ----------------------------
-- Table structure for course_purchases
-- ----------------------------
DROP TABLE IF EXISTS "public"."course_purchases";
CREATE TABLE "public"."course_purchases" (
  "id" int8 NOT NULL DEFAULT nextval('course_purchases_id_seq'::regclass),
  "created_at" timestamptz(6) NOT NULL,
  "modified_at" timestamptz(6) NOT NULL,
  "payment_id" int4 NOT NULL,
  "purchase_date" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "status" varchar(255) COLLATE "pg_catalog"."default" DEFAULT 'pending'::character varying,
  "student_id" int8 NOT NULL,
  "course_id" int8 NOT NULL
)
;

-- ----------------------------
-- Table structure for course_ratings
-- ----------------------------
DROP TABLE IF EXISTS "public"."course_ratings";
CREATE TABLE "public"."course_ratings" (
  "id" int4 NOT NULL DEFAULT nextval('course_ratings_id_seq'::regclass),
  "comment" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL,
  "modified_at" timestamp(6) NOT NULL,
  "rating" int4 DEFAULT 5,
  "student_id" int8 NOT NULL,
  "course_id" int8 NOT NULL
)
;

-- ----------------------------
-- Table structure for course_videos
-- ----------------------------
DROP TABLE IF EXISTS "public"."course_videos";
CREATE TABLE "public"."course_videos" (
  "id" int8 NOT NULL DEFAULT nextval('course_videos_id_seq'::regclass),
  "created_at" timestamp(6) NOT NULL,
  "description" varchar(500) COLLATE "pg_catalog"."default",
  "duration" int8,
  "modified_at" timestamp(6) NOT NULL,
  "number_of_order" int4 DEFAULT 0,
  "thumbnail_url" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "video_url" varchar(255) COLLATE "pg_catalog"."default",
  "course_id" int8 NOT NULL,
  "active" bool NOT NULL DEFAULT true
)
;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS "public"."courses";
CREATE TABLE "public"."courses" (
  "id" int8 NOT NULL DEFAULT nextval('courses_course_id_seq'::regclass),
  "course_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamptz(6) NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "modified_at" timestamptz(6) NOT NULL,
  "price" numeric(38,2),
  "status" varchar(255) COLLATE "pg_catalog"."default" DEFAULT 'active'::character varying,
  "subject" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "thumbnail_path" varchar(255) COLLATE "pg_catalog"."default",
  "tutor_id" int8 NOT NULL,
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for payment_logs
-- ----------------------------
DROP TABLE IF EXISTS "public"."payment_logs";
CREATE TABLE "public"."payment_logs" (
  "id" int8 NOT NULL DEFAULT nextval('payment_logs_id_seq'::regclass),
  "created_on" timestamptz(6),
  "payment_id" int8 NOT NULL,
  "payment_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "transaction_id" int8 NOT NULL,
  "verb" varchar(50) COLLATE "pg_catalog"."default",
  "data" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for payment_transactions
-- ----------------------------
DROP TABLE IF EXISTS "public"."payment_transactions";
CREATE TABLE "public"."payment_transactions" (
  "id" int8 NOT NULL DEFAULT nextval('payment_transactions_id_seq'::regclass),
  "created_at" timestamptz(6) NOT NULL,
  "modified_at" timestamptz(6) NOT NULL,
  "purchase_status" varchar(255) COLLATE "pg_catalog"."default",
  "reference_id" int8 NOT NULL,
  "reference_type" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for payments
-- ----------------------------
DROP TABLE IF EXISTS "public"."payments";
CREATE TABLE "public"."payments" (
  "id" int8 NOT NULL DEFAULT nextval('payments_id_seq'::regclass),
  "amount" numeric(38,2) DEFAULT 0,
  "created_at" timestamptz(6) NOT NULL,
  "currency" varchar(255) COLLATE "pg_catalog"."default" DEFAULT 'VND'::character varying,
  "modified_at" timestamptz(6) NOT NULL,
  "payment_method" varchar(255) COLLATE "pg_catalog"."default",
  "payment_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "payment_transaction_id" int8,
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "payment_transaction_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for tutors
-- ----------------------------
DROP TABLE IF EXISTS "public"."tutors";
CREATE TABLE "public"."tutors" (
  "id" int8 NOT NULL DEFAULT nextval('tutors_id_seq'::regclass),
  "joined_at" timestamp(6) NOT NULL,
  "resume" varchar(255) COLLATE "pg_catalog"."default",
  "subject" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "teach_fee" numeric(38,2) NOT NULL,
  "time_per_one_purchase" int8 NOT NULL,
  "account_id" int8 NOT NULL
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."accounts_id_seq"
OWNED BY "public"."accounts"."id";
SELECT setval('"public"."accounts_id_seq"', 18, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."availabilities_id_seq"
OWNED BY "public"."availabilities"."id";
SELECT setval('"public"."availabilities_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."booking_purchases_id_seq"
OWNED BY "public"."booking_purchases"."id";
SELECT setval('"public"."booking_purchases_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."bookings_id_seq"
OWNED BY "public"."bookings"."id";
SELECT setval('"public"."bookings_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."course_enrollments_id_seq"
OWNED BY "public"."course_enrollments"."id";
SELECT setval('"public"."course_enrollments_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."course_purchases_id_seq"
OWNED BY "public"."course_purchases"."id";
SELECT setval('"public"."course_purchases_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."course_ratings_id_seq"
OWNED BY "public"."course_ratings"."id";
SELECT setval('"public"."course_ratings_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."course_videos_id_seq"
OWNED BY "public"."course_videos"."id";
SELECT setval('"public"."course_videos_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."courses_course_id_seq"
OWNED BY "public"."courses"."id";
SELECT setval('"public"."courses_course_id_seq"', 23, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."payment_logs_id_seq"
OWNED BY "public"."payment_logs"."id";
SELECT setval('"public"."payment_logs_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."payment_transactions_id_seq"
OWNED BY "public"."payment_transactions"."id";
SELECT setval('"public"."payment_transactions_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."payments_id_seq"
OWNED BY "public"."payments"."id";
SELECT setval('"public"."payments_id_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."tutors_id_seq"
OWNED BY "public"."tutors"."id";
SELECT setval('"public"."tutors_id_seq"', 6, true);

-- ----------------------------
-- Uniques structure for table accounts
-- ----------------------------
ALTER TABLE "public"."accounts" ADD CONSTRAINT "uk_n7ihswpy07ci568w34q0oi8he" UNIQUE ("email");

-- ----------------------------
-- Checks structure for table accounts
-- ----------------------------
ALTER TABLE "public"."accounts" ADD CONSTRAINT "accounts_gender_check" CHECK (gender::text = ANY (ARRAY['MALE'::character varying, 'FEMALE'::character varying, 'OTHER'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table accounts
-- ----------------------------
ALTER TABLE "public"."accounts" ADD CONSTRAINT "accounts_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table availabilities
-- ----------------------------
ALTER TABLE "public"."availabilities" ADD CONSTRAINT "availabilities_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table booking_purchases
-- ----------------------------
ALTER TABLE "public"."booking_purchases" ADD CONSTRAINT "booking_purchases_purchase_status_check" CHECK (purchase_status::text = ANY (ARRAY['PENDING'::character varying, 'CONFIRMED'::character varying, 'CANCELED'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table booking_purchases
-- ----------------------------
ALTER TABLE "public"."booking_purchases" ADD CONSTRAINT "booking_purchases_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table bookings
-- ----------------------------
ALTER TABLE "public"."bookings" ADD CONSTRAINT "bookings_status_check" CHECK (status::text = ANY (ARRAY['PENDING'::character varying, 'CONFIRMED'::character varying, 'CANCELED'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table bookings
-- ----------------------------
ALTER TABLE "public"."bookings" ADD CONSTRAINT "bookings_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table course_enrollments
-- ----------------------------
ALTER TABLE "public"."course_enrollments" ADD CONSTRAINT "course_enrollments_status_check" CHECK (status::text = ANY (ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table course_enrollments
-- ----------------------------
ALTER TABLE "public"."course_enrollments" ADD CONSTRAINT "course_enrollments_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table course_purchases
-- ----------------------------
ALTER TABLE "public"."course_purchases" ADD CONSTRAINT "course_purchases_status_check" CHECK (status::text = ANY (ARRAY['PENDING'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table course_purchases
-- ----------------------------
ALTER TABLE "public"."course_purchases" ADD CONSTRAINT "course_purchases_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table course_ratings
-- ----------------------------
ALTER TABLE "public"."course_ratings" ADD CONSTRAINT "course_ratings_rating_check" CHECK (rating <= 5);

-- ----------------------------
-- Primary Key structure for table course_ratings
-- ----------------------------
ALTER TABLE "public"."course_ratings" ADD CONSTRAINT "course_ratings_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table course_videos
-- ----------------------------
ALTER TABLE "public"."course_videos" ADD CONSTRAINT "course_videos_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table courses
-- ----------------------------
ALTER TABLE "public"."courses" ADD CONSTRAINT "courses_status_check" CHECK (status::text = ANY (ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table courses
-- ----------------------------
ALTER TABLE "public"."courses" ADD CONSTRAINT "courses_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table payment_logs
-- ----------------------------
ALTER TABLE "public"."payment_logs" ADD CONSTRAINT "payment_logs_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table payment_transactions
-- ----------------------------
ALTER TABLE "public"."payment_transactions" ADD CONSTRAINT "payment_transactions_purchase_status_check" CHECK (purchase_status::text = ANY (ARRAY['PENDING'::character varying, 'APPROVED'::character varying, 'DECLINED'::character varying, 'REFUNDED'::character varying]::text[]));
ALTER TABLE "public"."payment_transactions" ADD CONSTRAINT "payment_transactions_reference_type_check" CHECK (reference_type::text = ANY (ARRAY['COURSE'::character varying, 'CALL'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table payment_transactions
-- ----------------------------
ALTER TABLE "public"."payment_transactions" ADD CONSTRAINT "payment_transactions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table payments
-- ----------------------------
ALTER TABLE "public"."payments" ADD CONSTRAINT "payments_payment_status_check" CHECK (payment_status::text = ANY (ARRAY['PENDING'::character varying, 'APPROVED'::character varying, 'DECLINED'::character varying, 'REFUND_PENDING'::character varying, 'REFUNDED'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table payments
-- ----------------------------
ALTER TABLE "public"."payments" ADD CONSTRAINT "payments_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table tutors
-- ----------------------------
ALTER TABLE "public"."tutors" ADD CONSTRAINT "uk_aiyni1rdhcjrt8mvxgcawknr0" UNIQUE ("account_id");

-- ----------------------------
-- Primary Key structure for table tutors
-- ----------------------------
ALTER TABLE "public"."tutors" ADD CONSTRAINT "tutors_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table availabilities
-- ----------------------------
ALTER TABLE "public"."availabilities" ADD CONSTRAINT "fkml8pdpruiejtkb40dx0olsehh" FOREIGN KEY ("tutor_id") REFERENCES "public"."tutors" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table booking_purchases
-- ----------------------------
ALTER TABLE "public"."booking_purchases" ADD CONSTRAINT "fk1eniahqlur99u9cvs0r43ixxb" FOREIGN KEY ("user_id") REFERENCES "public"."accounts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."booking_purchases" ADD CONSTRAINT "fkdd4nt6c6bggnkgiw15ultookq" FOREIGN KEY ("booking_id") REFERENCES "public"."bookings" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table bookings
-- ----------------------------
ALTER TABLE "public"."bookings" ADD CONSTRAINT "fkbtldj1dyykrk7op433dm58fws" FOREIGN KEY ("availability_id") REFERENCES "public"."availabilities" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."bookings" ADD CONSTRAINT "fkh6fs0mk4gmw5r7ivo49lhnrup" FOREIGN KEY ("student_id") REFERENCES "public"."accounts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table course_enrollments
-- ----------------------------
ALTER TABLE "public"."course_enrollments" ADD CONSTRAINT "fkf78cq7ecdpk1clt1w5ofnb34t" FOREIGN KEY ("course_id") REFERENCES "public"."courses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table course_purchases
-- ----------------------------
ALTER TABLE "public"."course_purchases" ADD CONSTRAINT "fklgj767mmd17234dkvgnfspw77" FOREIGN KEY ("course_id") REFERENCES "public"."courses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table course_ratings
-- ----------------------------
ALTER TABLE "public"."course_ratings" ADD CONSTRAINT "fk9cftxmmqr9mjf2wtf9dtc8fia" FOREIGN KEY ("course_id") REFERENCES "public"."courses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table course_videos
-- ----------------------------
ALTER TABLE "public"."course_videos" ADD CONSTRAINT "fklup8wored5r7ha1e40puhn1ba" FOREIGN KEY ("course_id") REFERENCES "public"."courses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table tutors
-- ----------------------------
ALTER TABLE "public"."tutors" ADD CONSTRAINT "fkp07mxy94ljr1clneif8jjng6f" FOREIGN KEY ("account_id") REFERENCES "public"."accounts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
