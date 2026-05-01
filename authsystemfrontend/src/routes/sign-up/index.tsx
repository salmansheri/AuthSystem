import {createFileRoute} from '@tanstack/react-router'
import {SignupForm} from "#/components/signup-form.tsx";

export const Route = createFileRoute('/sign-up/')({
    component: SignUpPage,
})

function SignUpPage() {
    return <div className="flex items-center justify-center min-h-screen">
        <SignupForm/>
    </div>
}
