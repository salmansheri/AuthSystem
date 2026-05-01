import { useMutation} from "@tanstack/react-query";
import { toast} from "sonner";
import { useNavigate} from "@tanstack/react-router";
import { signOutMutation } from "#/generated/@tanstack/react-query.gen.ts";

export const useSignOut = () => {
    const navigate = useNavigate();
    return useMutation({
        ...signOutMutation(),
        onError: (error) => {
            console.error(error);

            const message =
                error?.response?.data?.message ||
                error.message ||
                "something went Wrong!";

            toast.error(message);
        },
        onSuccess: (data) => {

            console.log(data);
            toast.success("sign in successfully created!");
            navigate({ to: "/sign-in"});
        },

    })

}