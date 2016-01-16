//
//  SignUp.m
//  UserData
//
//  Created by Eric Chaney on 1/15/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SignUp.h"
#import "Post.h"

@interface SignUp()



@end



@implementation SignUp

@synthesize userField;
@synthesize passField;
@synthesize emailField;


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)submitSignUp:(UIButton *)sender{
    [self signUpToParse];
    
    
}


- (void)signUpToParse {
    PFUser *user = [PFUser user];
    user.username = userField.text;
    user.password = passField.text;
    user.email = emailField.text;
    
    //    // other fields can be set just like with PFObject
    //    user[@"phone"] = @"415-392-0202";
    
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {
            [self dismissViewControllerAnimated:YES completion:nil];
            [self moveToPostView];

        } else {   NSString *errorString = [error userInfo][@"error"];   // Show the errorString somewhere and let the user try again.
        }
    }];
}


-(void)moveToPostView{
    UIStoryboard *mainStoryboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    Post *destViewController = [mainStoryboard instantiateViewControllerWithIdentifier:@"postView"];
    [self.navigationController pushViewController:destViewController animated:YES];
    [[UIApplication sharedApplication].keyWindow setRootViewController:destViewController];
}

@end
