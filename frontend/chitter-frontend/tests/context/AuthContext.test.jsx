import {render, screen, act, fireEvent} from '@testing-library/react';
import { AuthProvider, useAuth } from "../../src/services/context/AuthContext.jsx";

const TestComponent = () => {
    const { fName, lName, email, username, loggedIn, handleLogin, handleLogout } = useAuth();

    return (
        <>
            <button onClick={() => handleLogin({ fName: 'Ed', lName: 'Wright', email: 'ed@df.com', username: 'Ed Loves Testing' })}>Login</button>
            <button onClick={handleLogout}>Logout</button>
            <div>{`Logged in: ${loggedIn}`}</div>
            <div data-testid="fName">{fName}</div>
            <div data-testid="lName">{lName}</div>
            <div data-testid="email">{email}</div>
            <div data-testid="username">{username}</div>
        </>
    );
};

describe('Testing AuthContext', () => {

    describe("Testing initial state render", () => {
        beforeEach(() => {
            render(
                <AuthProvider>
                    <TestComponent />
                </AuthProvider>
            );
        });

        it("Should render login in as false", () => {
            expect(screen.getByText(/Logged in: false/i)).toBeInTheDocument();
        })

        it("Should render fName as empty string", () => {
            expect(screen.getByTestId('fName').textContent).toBe('');
        })

        it("Should render lName as empty string", () => {
            expect(screen.getByTestId('lName').textContent).toBe('');
        })

        it("Should render email as empty string", () => {
            expect(screen.getByTestId('email').textContent).toBe('');
        })

        it("Should render username as empty string", () => {
            expect(screen.getByTestId('username').textContent).toBe('');
        })
    })

    describe("Testing Login Functionality", () => {
        beforeEach(() => {
            render(
                <AuthProvider>
                    <TestComponent />
                </AuthProvider>
            );
            const loginButton = screen.getByText(/login/i);
            fireEvent.click(loginButton);
        });

        it('should show logged in as true after login', async () => {
            expect(await screen.findByText(/Logged in: true/i)).toBeInTheDocument();
        });

        it('should display the correct first name after login', () => {
            expect(screen.getByTestId('fName').textContent).toBe('Ed');
        });

        it('should display the correct last name after login', () => {
            expect(screen.getByTestId('lName').textContent).toBe('Wright');
        });

        it('should display the correct email after login', () => {
            expect(screen.getByTestId('email').textContent).toBe('ed@df.com');
        });

        it('should display the correct username after login', () => {
            expect(screen.getByTestId('username').textContent).toBe('Ed Loves Testing');
        });
    });

    describe("AuthContext - Logout Functionality", () => {
        beforeEach(() => {
            render(
                <AuthProvider>
                    <TestComponent />
                </AuthProvider>
            );
            const loginButton = screen.getByText(/login/i);
            const logoutButton = screen.getByText(/logout/i);

            fireEvent.click(loginButton);
            fireEvent.click(logoutButton);
        });

        it('should show logged in as false after logout', async () => {
            expect(await screen.findByText(/Logged in: false/i)).toBeInTheDocument();
        });

        it('should clear first name after logout', () => {
            expect(screen.getByTestId('fName').textContent).toBe('');
        });

        it('should clear last name after logout', () => {
            expect(screen.getByTestId('lName').textContent).toBe('');
        });

        it('should clear email after logout', () => {
            expect(screen.getByTestId('email').textContent).toBe('');
        });

        it('should clear username after logout', () => {
            expect(screen.getByTestId('username').textContent).toBe('');
        });
    });



    it('should reset state on logout', async () => {
        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const loginButton = screen.getByText(/login/i);
        const logoutButton = screen.getByText(/logout/i);


        fireEvent.click(loginButton);
        fireEvent.click(logoutButton);

        expect(await screen.findByText(/Logged in: false/i)).toBeInTheDocument();

    });

    it('should maintain state on re-render', () => {
        const { rerender } = render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        fireEvent.click(screen.getByText(/login/i));
        rerender(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        expect(screen.getByText(/Logged in: true/i)).toBeInTheDocument();
    });


    it('should not change state when logging out while not logged in', () => {
        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        fireEvent.click(screen.getByText(/logout/i));

        expect(screen.getByText(/Logged in: false/i)).toBeInTheDocument();
    });
});
