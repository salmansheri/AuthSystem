import {cn} from "#/lib/utils"
import {Button} from "#/components/ui/button"
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "#/components/ui/card"
import {Field, FieldDescription, FieldError, FieldGroup, FieldLabel,} from "#/components/ui/field"
import {Input} from "#/components/ui/input";
import * as z from 'zod';
import {useForm} from "@tanstack/react-form";
import {toast} from "sonner";
import {useSignUp} from "#/hooks/user/use-sign-up.ts";
import {Loader2} from "lucide-react";
import {Link} from "@tanstack/react-router";

const formSchema = z.object({
    name: z.string().min(5, "Name must be atleast 5 Characters"),
    email: z.string().email("Invalid Email Address"),
    password: z.string().min(4, "Password must be at least 5 Characters")
})

export function SignupForm({
                               className,
                               ...props
                           }: React.ComponentProps<"div">) {
    const signUpMutation = useSignUp();
    const form = useForm({
        defaultValues: {
            name: "",
            email: "",
            password: ""
        },
        validators: {
            onChange: formSchema,
            onBlur: formSchema
        },
        onSubmit: async ({value}) => {
            toast.success("Submitted");

            signUpMutation.mutate({
                body: {
                    email: value.email,
                    name: value.name,
                    password: value.password
                }
            })


        }
    })
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
                    <form onSubmit={(event) => {
                        event.preventDefault();
                        form.handleSubmit();
                    }
                    }>

                        <FieldGroup>
                            <form.Field name="name"
                                // biome-ignore lint/correctness/noChildrenProp: <explanation>
                                        children={(field) => {
                                            const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid;
                                            return (
                                                <Field data-invalid={isInvalid}>
                                                    <FieldLabel htmlFor="name">Full Name</FieldLabel>
                                                    <Input
                                                        id={field.name}
                                                        type={field.name}
                                                        value={field.state.value}
                                                        onBlur={field.handleBlur}
                                                        onChange={(event) => field.handleChange(event.target.value)}
                                                        aria-invalid={isInvalid}

                                                        placeholder="John Doe"
                                                        required/>
                                                    {isInvalid && (
                                                        <FieldError errors={field.state.meta
                                                            .errors}/>
                                                    )}
                                                </Field>

                                            )

                                        }}
                            />
                            <form.Field name="email"
                                // biome-ignore lint/correctness/noChildrenProp: <explanation>
                                        children={(field) => {
                                            const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid;
                                            return (
                                                <Field data-invalid={isInvalid}>
                                                    <FieldLabel htmlFor={field.name}>Email</FieldLabel>
                                                    <Input
                                                        id={field.name}
                                                        type="email"
                                                        name={field.name}
                                                        value={field.state.value}
                                                        onBlur={field.handleBlur}
                                                        onChange={(event) => field.handleChange(event.target.value)}
                                                        aria-invalid={isInvalid}
                                                        placeholder="m@example.com"
                                                        required
                                                    />
                                                    {isInvalid && (
                                                        <FieldError errors={field.state.meta
                                                            .errors}/>
                                                    )}
                                                </Field>

                                            )

                                        }}
                            />


                            <form.Field
                                name="password"
                                // biome-ignore lint/correctness/noChildrenProp: <explanation>
                                children={(field) => {
                                    const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid;

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
                                                        onChange={(event) => field.handleChange(event.target.value)}
                                                        placeholder="Enter Your Password"
                                                        type="password"
                                                        required/>
                                                    {isInvalid && (
                                                        <FieldError errors={field.state.meta
                                                            .errors}/>
                                                    )}
                                                </Field>

                                            </Field>
                                            <FieldDescription>
                                                Must be at least 4 characters long.
                                            </FieldDescription>
                                        </Field>
                                    )
                                }}

                            />
                            <Field>
                                <Button type="submit">
                                    {signUpMutation.isPending ? (
                                        <>
                                            <Loader2 className="mr-4 animate-spin"/>
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
    )
}
