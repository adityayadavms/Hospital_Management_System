


INSERT INTO insurance (created_at, policy_number, provider, valid_until)
VALUES
    ('2003-12-12', 'HDFC123', 'HDFC', '2030-12-12')
ON CONFLICT (policy_number) DO NOTHING;


INSERT INTO app_user (username, password, provider_type)
VALUES (
           'adminHospital@gmail.com',
           '$2a$10$RM/JhaWQpYCvCGao5LvuWOZ6WIxpRO1AM8A66KJZc0ScJ4hDforGy',
           'EMAIL'
       )
    ON CONFLICT (username) DO NOTHING;


INSERT INTO user_roles (user_id, roles)
SELECT id, 'ADMIN'
FROM app_user
WHERE username = 'adminHospital@gmail.com'
    ON CONFLICT DO NOTHING;