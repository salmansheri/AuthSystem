import { createFileRoute } from '@tanstack/react-router'
import {LoginForm} from "#/components/login-form.tsx";

export const Route = createFileRoute('/sign-in/')({
  component: SignInPage,
})

function SignInPage() {
  return <div className="min-h-screen flex items-center justify-center">
    <LoginForm />
  </div>
}
