INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES ('Rohan Sharma', '1998-05-15', 'rohan@gmail.com', 'male', 'B_POSITIVE')
    ON CONFLICT (email) DO NOTHING;

INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES ('Priya Singh', '1995-08-22', 'priya@gmail.com', 'female', 'A_POSITIVE')
    ON CONFLICT (email) DO NOTHING;

INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES ('Arjun Mehta', '2000-01-10', 'arjun@gmail.com', 'male', 'O_NEGATIVE')
    ON CONFLICT (email) DO NOTHING;

INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES ('Sneha Patel', '1990-11-30', 'sneha@gmail.com', 'female', 'AB_POSITIVE')
    ON CONFLICT (email) DO NOTHING;

INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES ('Vikram Das', '1985-07-04', 'vikram@gmail.com', 'male', 'O_POSITIVE')
    ON CONFLICT (email) DO NOTHING;