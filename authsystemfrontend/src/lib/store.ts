import {create} from 'zustand';
import {persist} from "zustand/middleware";
import type {SignInResponseDto, UserDto} from "#/generated";

const TOKEN_KEY = "auth_app";

//type AuthStatus = "IDLE" | "AUTHENTICATING" | "AUTHENTICATED" | "ANONYMOUS";

type AuthState = {
    accessToken: string | null;
    user?: UserDto | null;
    authStatus: boolean;
    expiresIn: number;
    authLoading: boolean;
    signIn: (signData: SignInResponseDto) => void;
    signOut: (silent?: boolean) => void;
}

export const useAuth = create<AuthState>()(
    persist(
        (set) => ({
            accessToken: null,
            user: null,
            authStatus: false,
            authLoading: false,
            expiresIn: 0,
            signIn: (signInData) => {
                console.log("SignIn started");

                set({ user: signInData.user, accessToken: signInData.accessToken, authStatus: true, expiresIn: signInData.expiresIn  })
                console.log("Auth State Stored Successfully!")


            },
            signOut: () => {
                set({authLoading: false, authStatus: false, expiresIn: 0, user: null, accessToken: null });
            }

        }),
        {
            name: TOKEN_KEY
        }
    )
);





