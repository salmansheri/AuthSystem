import {useMutation} from "@tanstack/react-query";
import {signUpMutation} from "#/generated/@tanstack/react-query.gen.ts";
import {toast} from "sonner";

export const useSignUp = () => {
    return useMutation({
        ...signUpMutation(),
        onError: (error) => {
            console.error(error);

            const message = error?.response?.data?.message || error.message || "something went Wrong!";

            toast.error(message);


        },
        onSuccess: (data) => {
            console.log(data);
            toast.success("signUp successfully created!");
        }
    })
}