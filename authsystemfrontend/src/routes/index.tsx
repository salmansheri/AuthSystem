import {createFileRoute} from '@tanstack/react-router'
import {Button} from "#/components/ui/button.tsx";
import {useSignOut} from "#/hooks/user/use-sign-out.ts";

export const Route = createFileRoute('/')({
  component: App,
})

function App() {
    const signOutMutation = useSignOut();

    const handleSignOut = () => {
        signOutMutation.mutate({
        });
    }


  return (
    <Button onClick={handleSignOut}>
          Sign out
      </Button>
  )
}
