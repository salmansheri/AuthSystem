import { useMutation } from "@tanstack/react-query";
import { toast } from "sonner";
import { signInMutation } from "#/generated/@tanstack/react-query.gen.ts";
import { useNavigate} from "@tanstack/react-router";

export const useSignIn = () => {
	const navigate = useNavigate();
	return useMutation({
		...signInMutation(),
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
			navigate({ to: "/"});
		},
	});
};
