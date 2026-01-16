-- Add is_admin column to users table (default false)
ALTER TABLE users
ADD COLUMN IF NOT EXISTS is_admin BOOLEAN NOT NULL DEFAULT FALSE;

-- ensure existing rows have false
UPDATE users SET is_admin = FALSE WHERE is_admin IS NULL;

COMMENT ON COLUMN users.is_admin IS 'Whether the user is an admin (superadmin)';
