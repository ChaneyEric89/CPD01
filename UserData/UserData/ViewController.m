//
//  ViewController.m
//  UserData
//
//  Created by Eric Chaney on 1/10/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#import "ViewController.h"
#import "Post.h"


@interface ViewController ()

@end

@implementation ViewController
@synthesize passEnterField;
@synthesize userEnterField;

- (void)viewDidLoad {
    
    
    
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        [self moveToPostView];
        
        NSLog(currentUser);
        // do stuff with the user
    } else {
        NSLog(@"No current USER");
        // show the signup or login screen
    }
    
    // Do any additional setup after loading the view, typically from a nib.
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)signUp:(UIButton *)sender
{
    NSLog(@"sign up Tapped!");
    [self moveToSignUp];
    
}

- (IBAction)logIn:(UIButton *)sender
{
    

   
    
    [PFUser logInWithUsernameInBackground:userEnterField.text password:passEnterField.text
                                    block:^(PFUser *user, NSError *error) {
                                        if (user) {
                                            NSLog(@"Logged in");
           
                                           
                                            [self moveToPostView];
                                       
                                            
                                        } else {
                                             //NSLog(error);
                                           NSLog(@"Error logging in");
                                            // The login failed. Check error to see why.
                                        }
                                    }];
    
    
    NSLog(@"log in Tapped!");
}

-(void)moveToPostView{
    UIStoryboard *mainStoryboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    Post *destViewController = [mainStoryboard instantiateViewControllerWithIdentifier:@"postView"];
    [self.navigationController pushViewController:destViewController animated:YES];
    [[UIApplication sharedApplication].keyWindow setRootViewController:destViewController];
}

-(void)moveToSignUp{
    UIStoryboard *mainStoryboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    Post *signUpViewController = [mainStoryboard instantiateViewControllerWithIdentifier:@"signUpView"];
    [self.navigationController pushViewController:signUpViewController animated:YES];
    [[UIApplication sharedApplication].keyWindow setRootViewController:signUpViewController];
}


@end
