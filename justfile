_default:
	@just --list

run:
	gradle run -q --console=plain

update_eclipse:
	# Update .classpath and eclipse related files. (for eclipse.jdt.ls support)
	gradle cleanEclipse eclipse

lint_all:
	pre-commit run --all-files

todo:
	rg ".(TODO|FIXME|FIX|HACK|WARN|PREF|NOTE): " --glob !{{ file_name(justfile()) }}

# vim: set ft=make :
