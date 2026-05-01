import { useForm } from "@tanstack/react-form";
import { Link } from "@tanstack/react-router";
import { Loader2 } from "lucide-react";
import { toast } from "sonner";
import * as z from "zod";
import { Button } from "#/components/ui/button";
import {
	Card,
	CardContent,
	CardDescription,
	CardHeader,
	CardTitle,
} from "#/components/ui/card";
import {
	Field,
	FieldDescription,
	FieldError,
	FieldGroup,
	FieldLabel,
} from "#/components/ui/field";
import { Input } from "#/components/ui/input";
import { useSignIn } from "#/hooks/user/use-sign-in.ts";
import { cn } from "#/lib/utils";

const formSchema = z.object({

	email: z.email("Invalid Email Address"),
	password: z.string().min(4, "Password must be at least 5 Characters"),
});

export function LoginForm({
	className,
	...props
}: React.ComponentProps<"div">) {
	const signInMutation = useSignIn();
	const form = useForm({
		defaultValues: {
			email: "",
			password: "",
		},
		validators: {
			onChange: formSchema,
			onBlur: formSchema,
		},
		onSubmit: async ({ value }) => {
			toast.success("Submitted");

			signInMutation.mutate({
				body: {
					email: value.email,

					password: value.password,
				},
			});
		},
	});
	return (
		<div className={cn("flex flex-col gap-6", className)} {...props}>
			<Card>
				<CardHeader className="text-center">
					<CardTitle className="text-xl">Create your account</CardTitle>
					<CardDescription>
						Enter your email below to create your account
					</CardDescription>
				</CardHeader>
				<CardContent>
					<form
						onSubmit={(event) => {
							event.preventDefault();
							form.handleSubmit();
						}}
					>
						<FieldGroup>
							<form.Field
								name="email"
								// biome-ignore lint/correctness/noChildrenProp: <explanation>
								children={(field) => {
									const isInvalid =
										field.state.meta.isTouched && !field.state.meta.isValid;
									return (
										<Field data-invalid={isInvalid}>
											<FieldLabel htmlFor={field.name}>Email</FieldLabel>
											<Input
												id={field.name}
												type="email"
												name={field.name}
												value={field.state.value}
												onBlur={field.handleBlur}
												onChange={(event) =>
													field.handleChange(event.target.value)
												}
												aria-invalid={isInvalid}
												placeholder="m@example.com"
												required
											/>
											{isInvalid && (
												<FieldError errors={field.state.meta.errors} />
											)}
										</Field>
									);
								}}
							/>

							<form.Field
								name="password"
								// biome-ignore lint/correctness/noChildrenProp: <explanation>
								children={(field) => {
									const isInvalid =
										field.state.meta.isTouched && !field.state.meta.isValid;

									return (
										<Field data-invalid={isInvalid}>
											<Field className="">
												<Field>
													<FieldLabel htmlFor={field.name}>Password</FieldLabel>
													<Input
														id={field.name}
														name={field.name}
														value={field.state.value}
														onBlur={field.handleBlur}
														onChange={(event) =>
															field.handleChange(event.target.value)
														}
														placeholder="Enter Your Password"
														type="password"
														required
													/>
													{isInvalid && (
														<FieldError errors={field.state.meta.errors} />
													)}
												</Field>
											</Field>
											<FieldDescription>
												Must be at least 4 characters long.
											</FieldDescription>
										</Field>
									);
								}}
							/>
							<Field>
								<Button type="submit">
									{signInMutation.isPending ? (
										<>
											<Loader2 className="mr-4 animate-spin" />
											Loading...
										</>
									) : (
										<>Create Account</>
									)}
								</Button>
								<FieldDescription className="text-center">
									Already have an account? <Link to="/sign-in">Sign in</Link>
								</FieldDescription>
							</Field>
						</FieldGroup>
					</form>
				</CardContent>
			</Card>
			<FieldDescription className="px-6 text-center">
				By clicking continue, you agree to our <a href="#">Terms of Service</a>{" "}
				and <a href="#">Privacy Policy</a>.
			</FieldDescription>
		</div>
	);
}
