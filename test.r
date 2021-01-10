library(dplyr)

# recursive function (extra parameters for generability in app)
solve = function(solutions, values, expressions, numVals, SOL, operations) {
    
    # base case: numVals == 1 (invariant: numVals will always equal the length of 'values' and 'expressions')
    if (numVals == 1 && values[1] == SOL)
        return(solutions %>% add_row(Solutions = expressions[1]))
    
    else if (numVals > 1) {
        
        # nested for loop for each pair of values in 'values'
        for (i in 1:(numVals-1)) {
            for (j in (i+1):numVals) {
                
                # recursive calls to reduce number of elements in 'values'
                if ("+" %in% operations)
                    solutions = solve(solutions, c(values[-j][-i], values[i]+values[j]), c(expressions[-j][-i], paste0("(", expressions[i], " + ", expressions[j], ")")), numVals - 1, SOL, operations)
                if ("-" %in% operations)
                    if (values[i] > values[j])
                        solutions = solve(solutions, c(values[-j][-i], values[i]-values[j]), c(expressions[-j][-i], paste0("(", expressions[i], " - ", expressions[j], ")")), numVals - 1, SOL, operations)
                    else
                        solutions = solve(solutions, c(values[-j][-i], values[j]-values[i]), c(expressions[-j][-i], paste0("(", expressions[j], " - ", expressions[i], ")")), numVals - 1, SOL, operations)
                if ("*" %in% operations)
                    solutions = solve(solutions, c(values[-j][-i], values[i]*values[j]), c(expressions[-j][-i], paste0("(", expressions[i], " * ", expressions[j], ")")), numVals - 1, SOL, operations)
                if ("/" %in% operations) {
                    if (values[j] != 0)
                        solutions = solve(solutions, c(values[-j][-i], values[i]/values[j]), c(expressions[-j][-i], paste0("(", expressions[i], " / ", expressions[j], ")")), numVals - 1, SOL, operations)
                    if (values[i] != 0)
                        solutions = solve(solutions, c(values[-j][-i], values[j]/values[i]), c(expressions[-j][-i], paste0("(", expressions[j], " / ", expressions[i], ")")), numVals - 1, SOL, operations)
                }
                if ("^" %in% operations && !(values[i] == 0 && values[j] == 0)) {
                    solutions = solve(solutions, c(values[-j][-i], values[i]^values[j]), c(expressions[-j][-i], paste0(expressions[i], " ^ ", expressions[j])), numVals - 1, SOL, operations)
                    solutions = solve(solutions, c(values[-j][-i], values[j]^values[i]), c(expressions[-j][-i], paste0(expressions[j], " ^ ", expressions[i])), numVals - 1, SOL, operations)
                }
                if ("log_a b" %in% operations) {
                    if (values[j] > 1 && values[i] > 0)
                        solutions = solve(solutions, c(values[-j][-i], log(values[i],values[j])), c(expressions[-j][-i], paste0("log_", expressions[j], " (", expressions[i], ")")), numVals - 1, SOL, operations)
                    if (values[i] > 1 && values[j] > 0)
                        solutions = solve(solutions, c(values[-j][-i], log(values[j],values[i])), c(expressions[-j][-i], paste0("log_", expressions[i], " (", expressions[j], ")")), numVals - 1, SOL, operations)
                }
            }
        }
    }
    return(solutions)
}

# test cases

solve(data.frame(Solutions = character()), c(24), c("24"), 1, 24, c("+", "-", "*", "/"))

solve(data.frame(Solutions = character()), c(13,11), c("13","11"), 2, 24, c("+", "-", "*", "/"))

solve(data.frame(Solutions = character()), c(13, 7, 4), c("13", "7", "4"), 3, 24, c("+", "-", "*", "/"))

solve(data.frame(Solutions = character()), c(11, 7, 4, 2), c("11", "7", "4", "2"), 4, 24, c("+", "-", "*", "/"))
